package com.example.ptuts3androidapp.Model.OCR;

import androidx.annotation.Nullable;

public class OcrErrorException extends Exception{
    @Nullable
    @Override
    public String getMessage() {
        return super.getMessage() + " bonjour non ";
    }
}
