package ru.myrosmol.conductor.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.ActivityAuthBinding
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.ui.main.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    private lateinit var binding: ActivityAuthBinding
    private val compositeDisposable = CompositeDisposable()
    private var wereAttemptToSendCode = false
    private var codeSent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        } else {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor = Color.TRANSPARENT
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.email.editText?.doAfterTextChanged {
            if (it.isNullOrBlank() || wereAttemptToSendCode.not())
                return@doAfterTextChanged

            if ("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$".toRegex().matches(it))
                binding.email.isErrorEnabled = false
            else
                binding.email.error = "Введён некорректный e-mail"
        }

        binding.sendCode.setOnClickListener {
            wereAttemptToSendCode = true
            binding.email.editText?.setText(binding.email.editText?.text)
            if (binding.email.isErrorEnabled.not())
                sendCode("${binding.email.editText?.text}")
        }

        binding.confirmCode.setOnClickListener {
            println("test")
            if (binding.code.editText?.text.isNullOrBlank())
                return@setOnClickListener

            println("test")
            confirmCode(
                "${binding.email.editText?.text}",
                "${binding.code.editText?.text}".toInt()
            )
        }
    }

    fun sendCode(mail: String) {
        showLoading()
        retrofitService.sendMailCode(mail) { response, code ->
            if (code == 200)
                codeSent = response!!.isDone == true
            Toast.makeText(
                this@AuthActivity,
                "Код подтверждения отправлен",
                Toast.LENGTH_LONG
            ).show()
            hideLoading()
        }
    }

    fun confirmCode(mail: String, code: Int) {
        showLoading()
        retrofitService.auth(mail, code) { response, code ->
            if (code == 200) {
                preferenceRepository.token = response!!.token.toString()
                println("new token: ${response.token}")

                Toast.makeText(
                    this@AuthActivity,
                    "Авторизация прошла успешно",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                finish()
            } else
                Toast.makeText(
                    this@AuthActivity,
                    "Произошла ошибка. Проверьте правильность данных",
                    Toast.LENGTH_LONG
                ).show()

            hideLoading()
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}