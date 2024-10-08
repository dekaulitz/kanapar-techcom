package com.github.dekalitz.kanaparktechcom.application.usecase;

import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;

public interface BaseUseCase<R, T> {
    public R execute(T dto) throws ApplicationException;
}
