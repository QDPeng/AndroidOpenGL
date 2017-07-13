//
// Created by peng on 2017/7/11.
//
#include "common.h"
#include "app_engine.h"

extern "C" {
void android_main(struct android_app *app);
}

void android_main(struct android_app *app) {
    app_dummy();
    AppEngine *engine = new AppEngine(app);
    engine->GameLoop();
    delete engine;
}