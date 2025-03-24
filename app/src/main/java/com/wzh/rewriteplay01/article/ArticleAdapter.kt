package com.wzh.rewriteplay01.article

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.wzh.core.Play
import com.wzh.core.databinding.AdapterArticleBinding
import com.wzh.core.util.checkNetworkAvailable
import com.wzh.core.util.getHtmlText
import com.wzh.core.util.setSafeListener
import com.wzh.core.util.showToast
import com.wzh.core.view.base.BaseRecyclerAdapter
import com.wzh.model.room.AppDatabase
import com.wzh.model.room.entity.Article
import com.wzh.model.room.entity.HISTORY
import com.wzh.rewriteplay01.R
import com.wzh.rewriteplay01.article.collect.CollectRepository
import com.wzh.rewriteplay01.article.collect.CollectRepositoryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArticleAdapter(
    private val mContext: Context,
    private val articleList: ArrayList<Article>,
    private val isShowCollect: Boolean = true,
) : BaseRecyclerAdapter<AdapterArticleBinding>(), CoroutineScope by MainScope() {
    //通过实现 `CoroutineScope`，你可以直接在类中使用 `launch` 和 `async` 启动协程，而无需显式传递 `CoroutineContext`。

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerHolder<AdapterArticleBinding> {
        val binding =
            AdapterArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BaseRecyclerHolder(binding)
    }

    private fun setCollect(
        collectRepository: CollectRepository,
        t: Article,
        articleTvCollect: ImageView
    ) {
        launch(Dispatchers.IO) {
            val articleDao = AppDatabase.getDatabase(mContext).browseHistoryDao()
            if (!t.collect) {
                val cancelCollects = collectRepository.cancelCollects(t.id)
                if (cancelCollects.errorCode == 0) {
                    withContext(Dispatchers.Main) {
                        articleTvCollect.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                        mContext.showToast(mContext.getString(R.string.collection_cancelled_successfully))
                        articleDao.update(t)
                    }
                } else {
                    mContext.showToast(mContext.getString(R.string.failed_to_cancel_collection))
                }
            } else {
                val toCollects = collectRepository.toCollects(t.id)
                if (toCollects.errorCode == 0) {
                    withContext(Dispatchers.Main) {
                        articleTvCollect.setImageResource(R.drawable.ic_favorite_black_24dp)
                        mContext.showToast(mContext.getString(R.string.collection_successful))
                        articleDao.update(t)
                    }
                } else {
                    mContext.showToast(mContext.getString(R.string.collection_failed))
                }
            }
        }
    }

    override fun onBaseBindViewHolder(position: Int, binding: AdapterArticleBinding) {
        val data = articleList[position]
        val collectRepository = EntryPointAccessors.fromApplication(
            mContext,
            CollectRepositoryPoint::class.java
        ).collectRepository()
        binding.apply {
            if (!TextUtils.isEmpty(data.title))
                articleTvTitle.text = getHtmlText(data.title)
            articleTvChapterName.text = data.superChapterName
            articleTvAuthor.text =
                if (TextUtils.isEmpty(data.author)) data.shareUser else data.author
            articleTvTime.text = data.niceShareDate
            if (!TextUtils.isEmpty(data.envelopePic)) {
                articleIvImg.visibility = VISIBLE
                Glide.with(mContext).load(data.envelopePic).into(articleIvImg)
            } else {
                articleIvImg.visibility = GONE
            }
            articleTvTop.isVisible = data.type > 0
            articleTvNew.isVisible = data.fresh

            articleIvCollect.isVisible = isShowCollect
            if (data.collect) {
                articleIvCollect.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                articleIvCollect.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
            launch {
                Play.isLogin().collectLatest {
                    articleIvCollect.isVisible = it
                }
            }
            articleIvCollect.setSafeListener {
                launch {
                    Play.isLogin().collectLatest {
                        if (it) {
                            if (mContext.checkNetworkAvailable()) {
                                data.collect = !data.collect
                                setCollect(collectRepository, data, articleIvCollect)
                            } else {
                                mContext.showToast(mContext.getString(R.string.no_network))
                            }
                        } else {
                            mContext.showToast(mContext.getString(R.string.not_currently_logged_in))
                        }
                    }
                }
            }
            articleLlItem.setOnClickListener {
                if (!mContext.checkNetworkAvailable()) {
                    mContext.showToast(mContext.getString(R.string.no_network))
                    return@setOnClickListener
                }
//                ArticleActivity.actionStart(mContext, data)//没实现这个活动，先别跳
                val browseHistoryDao = AppDatabase.getDatabase(mContext).browseHistoryDao()
                launch(Dispatchers.IO) {
                    if (browseHistoryDao.getArticle(data.id, HISTORY) == null) {
                        data.localType = HISTORY
                        data.desc = ""
                        browseHistoryDao.insert(data)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}
