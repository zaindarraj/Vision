package com.example.vision.session_management.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.datastore.core.Serializer;
import androidx.datastore.*;

import java.io.InputStream;
import java.io.OutputStream;
import com.example.vision.Token;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class TokenSerializer implements Serializer<Token> {
    @Override
    public Token getDefaultValue() {
        return Token.getDefaultInstance();
    }

    @Nullable
    @Override
    public Token readFrom(@NonNull InputStream inputStream, @NonNull Continuation<? super Token> continuation) {
        try {
            return Token.parseFrom(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Nullable
    @Override
    public Object writeTo(Token token, @NonNull OutputStream outputStream, @NonNull Continuation<? super Unit> continuation) {
        return null;
    }
}
