package com.wzh.rewriteplay01.project

import androidx.lifecycle.LiveData
import com.wzh.core.view.base.BaseAndroidViewModel
import com.wzh.model.room.entity.ProjectClassify
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : BaseAndroidViewModel<List<ProjectClassify>, Unit, Boolean>(){
    var position = 0
    init{
        getDataList(false)
    }

    override fun getData(page:Boolean):LiveData<Result<List<ProjectClassify>>>{
        return projectRepository.getProjectTree(page)
    }

}