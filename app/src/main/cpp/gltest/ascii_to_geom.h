//
// Created by peng on 2017/7/12.
//

#ifndef ANDROIDOPENGL_ASCII_TO_GEOM_H
#define ANDROIDOPENGL_ASCII_TO_GEOM_H

#include "simplegeom.h"

/* Converts ASCII art into a Vbo/Ibo pair. Useful for retro-looking drawings/text!
 * scale is the size of each character. The center of the rendering will be 0,0.
 *
 * Examples:
 *
 * Square:
 *     +---+
 *     |   |
 *     +---+
 *
 * Triangle:
 *     +-----+
 *      `   /
 *       ` /
 *        +
 *
 * The + sign represents a vertex; lines are represented by -, /, ` and |.
 */
SimpleGeom *AsciiArtToGeom(const char *art, float scale);

#endif //ANDROIDOPENGL_ASCII_TO_GEOM_H
