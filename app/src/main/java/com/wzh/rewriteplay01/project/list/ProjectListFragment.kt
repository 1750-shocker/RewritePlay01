package com.wzh.rewriteplay01.project.list

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.wzh.model.pojo.QueryArticle
import com.wzh.rewriteplay01.article.ArticleAdapter
import com.wzh.rewriteplay01.base.BaseListFragment
import dagger.hilt.android.AndroidEntryPoint


private const val PROJECT_CID = "PROJECT_CID"

@AndroidEntryPoint
class ProjectListFragment : BaseListFragment() {

    private val viewModel by viewModels<ProjectListViewModel>()

    private var projectCid: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            projectCid = it.getInt(PROJECT_CID)
        }
    }


    override fun initView() {
        articleAdapter = ArticleAdapter(requireContext(), viewModel.dataList)
        super.initView()
    }


    override fun initData() {
        setDataStatus(viewModel.dataLiveData, {
            if (viewModel.dataList.size > 0) loadFinished()
        }) {
            if (page == 1 && viewModel.dataList.size > 0) {
                viewModel.dataList.clear()
            }
            viewModel.dataList.addAll(it)
            articleAdapter.notifyItemInserted(it.size)
        }
        getArticleList(false)
    }

    override fun refreshData() {
        getArticleList(true)
    }

    private fun getArticleList(isRefresh: Boolean) {
        if (viewModel.dataList.size <= 0) {
            startLoading()
            projectCid?.apply {
                viewModel.getDataList(QueryArticle(page, this, isRefresh))
            }
        }
    }

    override fun isHaveHeadMargin(): Boolean {
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance(cid: Int) =
            ProjectListFragment().apply {
                arguments = Bundle().apply {
                    putInt(PROJECT_CID, cid)
                }
            }
    }

}
