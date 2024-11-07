package com.github.dekalitz.kanaparktechcom.application.usecase;

import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;

public interface UseCase<R, T> {
    public R execute(T dto) throws ApplicationException;

}
