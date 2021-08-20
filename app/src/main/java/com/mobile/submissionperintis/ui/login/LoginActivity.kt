package com.mobile.submissionperintis.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.mobile.submissionperintis.base.BaseActivity
import com.mobile.submissionperintis.databinding.ActivityLoginBinding
import com.mobile.submissionperintis.extension.startActivity
import com.mobile.submissionperintis.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.json.JSONException
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LoginActivity : BaseActivity() {

    private var _job: Job? = null
    private var _callbackManager: CallbackManager? = null
    private var _binding: ActivityLoginBinding? = null
    private val _viewModel: LoginViewModel by viewModel()
    private var _googleSignClient: GoogleSignInClient? = null
    override fun contentView(): View {
        return ActivityLoginBinding.inflate(layoutInflater).also {
            _binding = it
        }.root
    }

    override fun setupViews() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        _googleSignClient = GoogleSignIn.getClient(this@LoginActivity, gso);
        _callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(_callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val request = GraphRequest.newMeRequest(
                        loginResult?.accessToken
                    ) { obj, response ->
                        try {
                            val firstName = obj.getString("first_name")
                            val lastName = obj.getString("last_name")
                            val email = obj.getString("email")
                            val id = obj.getString("id")
                            val imgUrl = "https://graph.facebook.com/$id/picture?type=normal"
                            _viewModel.loginButtonClicked("$firstName $lastName", email, imgUrl)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString("fields", "first_name,last_name,email,id")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })
    }

    override fun setupViewEvents() {
        _binding?.apply {
            btnLogin.setOnClickListener {
                _viewModel.loginButtonClicked(editName.text.toString(), null, null)
            }
            btnFbLogin.setOnClickListener {
                LoginManager.getInstance()
                    .logInWithReadPermissions(this@LoginActivity, listOf("public_profile", "email"))
            }
            btnGoogleLogin.setOnClickListener {
                val signInIntent: Intent? = _googleSignClient?.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)

            }
        }
    }

    override fun bindViewModel() {
        _job = _viewModel.eventsFlow
            .onEach {
                when (it) {
                    LoginViewModel.Event.NavigateToDashboard -> {
                        startActivity<MainActivity>()
                    }
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun onDestroy() {
        super.onDestroy()
        _job?.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        _callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            _viewModel.loginButtonClicked(
                account.displayName,
                account.email,
                account.photoUrl?.toString()
            )
        } catch (e: ApiException) {
        }
    }

    companion object {
        private val RC_SIGN_IN = 100
    }
}