//
// Created by peng on 2017/7/12.
//

#ifndef ANDROIDOPENGL_INDEXBUF_H
#define ANDROIDOPENGL_INDEXBUF_H

#include "common.h"

/* Represents an index buffer (IBO). */
class IndexBuf {
public:
    IndexBuf(GLushort *data, int dataSizeBytes);

    ~IndexBuf();

    void BindBuffer();

    void UnbindBuffer();

    int GetCount() { return mCount; }

private:
    GLuint mIbo;
    int mCount;
};

#endif //ANDROIDOPENGL_INDEXBUF_H
