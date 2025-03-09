package com.wzh.rewriteplay01.project.list

import androidx.lifecycle.LiveData
import com.wzh.core.view.base.BaseAndroidViewModel
import com.wzh.model.pojo.QueryArticle
import com.wzh.model.room.entity.Article
import com.wzh.rewriteplay01.project.ProjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseAndroidViewModel<List<Article>, Article, QueryArticle>() {

    override fun getData(page: QueryArticle): LiveData<Result<List<Article>>> {
        return projectRepository.getProject(page)
    }

}