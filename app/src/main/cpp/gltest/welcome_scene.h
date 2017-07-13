//
// Created by peng on 2017/7/12.
//

#ifndef ANDROIDOPENGL_WELCOME_SCENE_H
#define ANDROIDOPENGL_WELCOME_SCENE_H

#include "ui_scene.h"
#include "util.hpp"

/* The "welcome scene" (main menu) */
class WelcomeScene : public UiScene {
protected:
    // IDs for our buttons:
    int mPlayButtonId;
    int mStoryButtonId;
    int mAboutButtonId;

    virtual void RenderBackground();

    virtual void OnButtonClicked(int id);

    void UpdateWidgetStates();

public:
    WelcomeScene();

    ~WelcomeScene();

    virtual void OnCreateWidgets();

    virtual void OnStartGraphics();

    virtual void OnKillGraphics();

    virtual void DoFrame();
};


#endif //ANDROIDOPENGL_WELCOME_SCENE_H
