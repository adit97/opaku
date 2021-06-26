package com.opaku.id.core.domain.usecase

import com.opaku.id.core.domain.repository.IAppRepository
import javax.inject.Inject

class AppInteractor @Inject constructor(private val appRepository: IAppRepository): AppUseCase {
}