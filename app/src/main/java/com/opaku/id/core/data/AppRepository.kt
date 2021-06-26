package com.opaku.id.core.data

import com.opaku.id.core.data.source.local.LocalDataSource
import com.opaku.id.core.data.source.remote.RemoteDataSource
import com.opaku.id.core.domain.repository.IAppRepository
import com.opaku.id.core.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppRepository {

}