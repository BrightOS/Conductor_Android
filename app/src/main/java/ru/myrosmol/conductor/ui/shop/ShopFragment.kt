package ru.myrosmol.conductor.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentShopBinding
import ru.myrosmol.conductor.epoxy.product
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.ProductResponse
import javax.inject.Inject

@AndroidEntryPoint
class ShopFragment : Fragment() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    lateinit var binding: FragmentShopBinding

    private val onProductBoughtListener = object : OnProductBoughtListener {
        override fun onProductBought(productId: Int) {
            buyProduct(productId)
        }
    }

    val productsList = MutableLiveData<List<ProductResponse>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsList.observe(viewLifecycleOwner) {
            binding.usersRecycler.withModels {
                it.forEach {
                    product {
                        id(it.id)
                        alreadyBought(it.alreadyBought!!)
                        title(it.title)
                        description(it.description)
                        cost(it.cost!!)
                        onProductBoughtListener(onProductBoughtListener)
                    }
                }
                hideLoading()
            }
        }

        updateProductsList()
    }

    fun updateProductsList() {
        showLoading()
        retrofitService.getAllProducts(preferenceRepository.token) { response, code ->
            if (code == 200)
                productsList.postValue(response)
            else
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    fun buyProduct(productId: Int) {
        showLoading()
        retrofitService.buyProduct(preferenceRepository.token, productId) { response, code ->
            if (code == 200) {
                if (response?.isDone == true)
                    updateProductsList()
                else {
                    Toast.makeText(
                        context,
                        "Недостаточно средств для покупки.",
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                }
            } else {
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
                hideLoading()
            }
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

}