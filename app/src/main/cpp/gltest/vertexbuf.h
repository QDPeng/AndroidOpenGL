//
// Created by peng on 2017/7/12.
//

#ifndef ANDROIDOPENGL_VERTEXBUF_H
#define ANDROIDOPENGL_VERTEXBUF_H

#include "common.h"

class VertexBuf {
private:
    GLuint mVbo;
    GLenum mPrimitive;
    int mStride;
    int mColorsOffset;
    int mTexCoordsOffset;
    int mCount;

public:
    VertexBuf(GLfloat *geomData, int dataSize, int stride);

    ~VertexBuf();

    void BindBuffer();

    void UnbindBuffer();

    int GetStride() { return mStride; }

    int GetCount() { return mCount; }

    int GetPositionsOffset() { return 0; }

    bool HasColors() { return mColorsOffset > 0; }

    int GetColorsOffset() { return mColorsOffset; }

    void SetColorsOffset(int offset) { mColorsOffset = offset; }

    bool HasTexCoords() { return mTexCoordsOffset > 0; }

    void SetTexCoordsOffset(int offset) { mTexCoordsOffset = offset; }

    int GetTexCoordsOffset() { return mTexCoordsOffset; }

    GLenum GetPrimitive() { return mPrimitive; }

    void SetPrimitive(GLenum primitive) { mPrimitive = primitive; }
};

#endif //ANDROIDOPENGL_VERTEXBUF_H
