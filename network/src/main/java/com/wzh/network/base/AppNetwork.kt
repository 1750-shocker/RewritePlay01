package com.wzh.network.base

import com.wzh.network.service.ProjectService

object AppNetwork {
    private val projectService = ServiceCreator.create(ProjectService::class.java)

    suspend fun getProjectTree() = projectService.getProjectTree()

    suspend fun getProject(page: Int, cid: Int) = projectService.getProject(page, cid)
}