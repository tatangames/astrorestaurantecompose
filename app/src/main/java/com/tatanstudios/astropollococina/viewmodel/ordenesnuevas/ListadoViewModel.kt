package com.tatanstudios.astropollococina.viewmodel.ordenesnuevas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatanstudios.astropollococina.extras.Event
import com.tatanstudios.astropollococina.model.ordenes.ModeloCategorias
import com.tatanstudios.astropollococina.model.ordenes.ModeloDatosBasicos
import com.tatanstudios.astropollococina.model.ordenes.ModeloInfoProducto
import com.tatanstudios.astropollococina.model.ordenes.ModeloListaProductoCategorias
import com.tatanstudios.astropollococina.model.ordenes.ModeloNuevasOrdenes
import com.tatanstudios.astropollococina.model.ordenes.ModeloOrdenesCanceladas
import com.tatanstudios.astropollococina.model.ordenes.ModeloOrdenesCompletadas
import com.tatanstudios.astropollococina.model.ordenes.ModeloOrdenesPreparacion
import com.tatanstudios.astropollococina.model.ordenes.ModeloProductoOrdenes
import com.tatanstudios.astropollococina.network.RetrofitBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NuevasOrdenesViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloNuevasOrdenes>>()
    val resultado: LiveData<Event<ModeloNuevasOrdenes>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun nuevasOrdenesRetrofit(idusuario: String, idfirebase: String?) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoNuevasOrdenas(idusuario, idfirebase)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}


class ListadoProductosViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloProductoOrdenes>>()
    val resultado: LiveData<Event<ModeloProductoOrdenes>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun listadoProductosRetrofit(idorden: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoProductosOrden(idorden)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}



class CancelarOrdenViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloDatosBasicos>>()
    val resultado: LiveData<Event<ModeloDatosBasicos>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun cancelarOrdenRetrofit(idorden: Int, notaCancelar: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().cancelarOrden(idorden, notaCancelar)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}



class IniciarOrdenViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloDatosBasicos>>()
    val resultado: LiveData<Event<ModeloDatosBasicos>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun iniciarOrdenRetrofit(idorden: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().iniciarOrden(idorden)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}




class ProductosOrdenViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloProductoOrdenes>>()
    val resultado: LiveData<Event<ModeloProductoOrdenes>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun productosOrdenRetrofit(idorden: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoProductosOrden(idorden)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}



class InfoProductoViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloInfoProducto>>()
    val resultado: LiveData<Event<ModeloInfoProducto>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun infoProductoRetrofit(idproducto: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().infoProductoIndividual(idproducto)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}


class OrdenPreparacionBuscarViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloOrdenesPreparacion>>()
    val resultado: LiveData<Event<ModeloOrdenesPreparacion>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun prepracionOrdenRetrofit(id: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoOrdenesPreparacion(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}




class FinalizarOrdenViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloDatosBasicos>>()
    val resultado: LiveData<Event<ModeloDatosBasicos>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun finalizarOrdenRetrofit(idorden: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().finalizarOrden(idorden)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}




class OrdenCompletadasBuscarViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloOrdenesCompletadas>>()
    val resultado: LiveData<Event<ModeloOrdenesCompletadas>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun completadasOrdenRetrofit(id: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoOrdenesCompletadas(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}




class OrdenCanceladasBuscarViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloOrdenesCanceladas>>()
    val resultado: LiveData<Event<ModeloOrdenesCanceladas>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun canceladasOrdenRetrofit(id: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoOrdenesCanceladas(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}



class CategoriasBuscarViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloCategorias>>()
    val resultado: LiveData<Event<ModeloCategorias>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun categoriasRetrofit(id: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoCategorias(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}





class ActualizarCategoriaViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloDatosBasicos>>()
    val resultado: LiveData<Event<ModeloDatosBasicos>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun actualizarCategoriasRetrofit(idcategoria: Int, valorcheck: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().actualizarCategoria(idcategoria, valorcheck)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}


class ListadoProductosCategoriaViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloListaProductoCategorias>>()
    val resultado: LiveData<Event<ModeloListaProductoCategorias>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun listaProductosCategoriasRetrofit(idcategoria: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().listadoProductosCategoria(idcategoria)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}




class ActualizarProductosViewModel() : ViewModel() {

    private val _resultado = MutableLiveData<Event<ModeloDatosBasicos>>()
    val resultado: LiveData<Event<ModeloDatosBasicos>> get() = _resultado

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var disposable: Disposable? = null
    private var isRequestInProgress = false

    fun actualizarProductoCateRetrofit(idproducto: Int, valorcheck: Int) {
        if (isRequestInProgress) return

        isRequestInProgress = true

        _isLoading.value = true
        disposable = RetrofitBuilder.getApiService().actualizarEstadoProducto(idproducto, valorcheck)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry()
            .subscribe(
                { response ->
                    _isLoading.value = false
                    _resultado.value = Event(response)
                    isRequestInProgress = false
                },
                { error ->
                    _isLoading.value = false
                    isRequestInProgress = false
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose() // Limpiar la suscripción
    }
}


