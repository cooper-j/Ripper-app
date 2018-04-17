package com.hexan.ripper.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.hexan.ripper.R
import com.hexan.ripper.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity(), LoginMvpView {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter.create()
        presenter.attachView(this)

        if (presenter.isLoggedIn()) {
            loginUser()
        } else {
            rootLayout.visibility = View.VISIBLE
            // Set up the login form.
            passwordEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    loginProgressBar.visibility = View.VISIBLE
                    onUserLoginAction()
                    presenter.attemptLogin(usernameEditText.text.toString(), passwordEditText.text.toString())
                    return@OnEditorActionListener true
                }
                false
            })

            login_button.setOnClickListener {
                loginProgressBar.visibility = View.VISIBLE
                presenter.attemptLogin(usernameEditText.text.toString(), passwordEditText.text.toString())
            }
        }
    }

    private fun onUserLoginAction() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun loginUser() {
        loginProgressBar.visibility = View.GONE
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun showError(message: String?) {
        loginProgressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
