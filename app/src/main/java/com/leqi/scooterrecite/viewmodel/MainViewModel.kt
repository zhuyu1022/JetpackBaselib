package com.leqi.scooterrecite.viewmodel

import androidx.lifecycle.MutableLiveData
import com.leqi.baselib.base.viewModel.BaseViewModel
import com.leqi.baselib.ext.request
import com.leqi.baselib.ext.requestParseData
import com.leqi.baselib.ext.toast
import com.leqi.baselib.state.Status
import com.leqi.baselib.state.UiState
import com.leqi.scooterrecite.model.ApiPagerResponse
import com.leqi.scooterrecite.model.ApiResponse
import com.leqi.scooterrecite.model.Article
import com.leqi.scooterrecite.network.HttpRequestManger

class MainViewModel : BaseViewModel() {


    var pageNo = 0
    var articlrList = MutableLiveData<UiState<ApiResponse<ApiPagerResponse<Article>>>>()
    fun getHomeArticleList() {
        requestParseData(liveData = articlrList, request = {
            HttpRequestManger.apiService.getAritrilList(pageNo)
        }, success = {
            if (it.data==null||it.data?.data?.datas.isNullOrEmpty()){
                it.status=Status.EMPTY
                articlrList.postValue(it)
            }
            pageNo++
        }, error = {
            it.errorMsg.toast()
        })
    }


}