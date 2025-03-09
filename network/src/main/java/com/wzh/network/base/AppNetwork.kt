package com.wzh.network.base

import com.wzh.network.service.CollectService
import com.wzh.network.service.ProjectService

object AppNetwork {
    private val projectService = ServiceCreator.create(ProjectService::class.java)

    suspend fun getProjectTree() = projectService.getProjectTree()

    suspend fun getProject(page: Int, cid: Int) = projectService.getProject(page, cid)

    private val collectService = ServiceCreator.create(CollectService::class.java)

    suspend fun getCollectList(page: Int) = collectService.getCollectList(page)

    suspend fun toCollect(id: Int) = collectService.toCollect(id)

    suspend fun cancelCollect(id: Int) = collectService.cancelCollect(id)
}