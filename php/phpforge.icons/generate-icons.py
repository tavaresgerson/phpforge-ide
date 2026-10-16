#!/usr/bin/env python3
"""Gera o icon set Lucide do PHPForge IDE (estilo JetBrains New UI).

Rasteriza SVGs do Lucide para os resource names que os módulos NetBeans
carregam (espelhando os pacotes-alvo em icons-src/). O build do phpforge.icons
empacota tudo em locale-JARs (*_nb.jar) ao lado dos JARs originais — o
classloader do módulo dá preferência ao locale JAR, substituindo os ícones.

Para cada recurso gera:
  <res>.<ext>        PNG (16px, ou 24px para nomes *24)
  <res>@2x.<ext>     PNG (32px / 48px) para HiDPI
  <res>.svg          SVG Lucide recolorido (os módulos modernos preferem SVG)
"""
import os
import re
import sys

import cairosvg
from PIL import Image, ImageDraw, ImageFont

LUCIDE = "/tmp/lucide/package/icons"
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "icons-src")
COLOR = "#A9B0B8"

PHP_RES_DIR = "org/netbeans/modules/php/editor/resources"
FONT = os.path.normpath(os.path.join(
    os.path.dirname(os.path.abspath(__file__)), "..", "brand",
    "fonts", "SpaceGrotesk-Bold.ttf"))

# glyph-outline colorido (cores JetBrains) para os tipos PHP
PHP_TYPES = {
    "class": ("C", "#40B6E0"),
    "interface": ("I", "#59A869"),
    "trait": ("T", "#D9A343"),
    "enum": ("E", "#DB5C98"),
    "enumCase": ("e", "#C77DBB"),
    "function": ("f", "#F1C36D"),
    "constant": ("K", "#9676AA"),
    "methodPublic": ("m", "#F1C36D"),
    "methodProtected": ("m", "#D9A343"),
    "methodStaticPublic": ("s", "#F1C36D"),
    "methodStaticProtected": ("s", "#D9A343"),
    "fieldPublic": ("F", "#59A869"),
    "fieldProtected": ("F", "#D9A343"),
    "fieldPrivate": ("F", "#DB5860"),
}

# (pacote alvo, [(resource, extensão, ícone lucide), ...])
TARGETS = [
    ("org/openide/resources/actions", [
        ("save", "png", "save"), ("save24", "png", "save"),
        ("undo", "gif", "undo-2"), ("undo24", "gif", "undo-2"),
        ("redo", "gif", "redo-2"), ("redo24", "gif", "redo-2"),
        ("copy", "gif", "copy"), ("copy24", "gif", "copy"),
        ("cut", "gif", "scissors"), ("cut24", "gif", "scissors"),
        ("paste", "gif", "clipboard-paste"), ("paste24", "gif", "clipboard-paste"),
        ("delete", "gif", "trash-2"),
        ("find", "gif", "search"), ("find24", "gif", "search"),
        ("print", "png", "printer"), ("print24", "png", "printer"),
        ("open", "gif", "folder-open"),
        ("properties", "gif", "settings"),
        ("execute", "gif", "play"),
        ("build", "gif", "hammer"),
        ("compile", "gif", "hammer"),
        ("clean", "gif", "eraser"),
        ("clone", "gif", "copy-plus"),
        ("nextTab", "gif", "chevron-right"),
        ("previousTab", "gif", "chevron-left"),
    ]),
    ("org/openide/loaders", [
        ("saveAll", "gif", "save-all"), ("saveAll24", "gif", "save-all"),
        ("defaultFolder", "gif", "folder"),
        ("defaultFolderOpen", "gif", "folder-open"),
        ("unknown", "gif", "file-question"),
    ]),
    ("org/netbeans/modules/project/ui/resources", [
        ("newFile", "png", "file-plus"), ("newFile24", "png", "file-plus"),
        ("newProject", "png", "file-plus-2"), ("newProject24", "png", "file-plus-2"),
        ("openProject", "png", "folder-open"), ("openProject24", "png", "folder-open"),
        ("open", "gif", "folder-open"),
        ("buildProject", "png", "hammer"), ("buildProject24", "png", "hammer"),
        ("cleanProject", "gif", "eraser"), ("cleanProject24", "gif", "eraser"),
        ("closeProject", "gif", "x"),
        ("runProject", "png", "play"), ("runProject24", "png", "play"),
        ("testProject", "png", "flask-conical"), ("testProject24", "png", "flask-conical"),
        ("compileSingle", "png", "hammer"),
        ("filesTab", "png", "files"),
        ("projectTab", "png", "folder"),
    ]),
]


def render_lucide(name, size, dest):
    with open(os.path.join(LUCIDE, name + ".svg")) as f:
        svg = f.read()
    svg = svg.replace("currentColor", COLOR)
    cairosvg.svg2png(bytestring=svg.encode(), write_to=dest,
                     output_width=size, output_height=size)


def render_php_glyph(letter, color, size, dest):
    scale = 4
    big = size * scale
    img = Image.new("RGBA", (big, big), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)
    font = ImageFont.truetype(FONT, int(big * 0.72))
    draw.text((big / 2, big / 2), letter, font=font, anchor="mm",
              fill=(0, 0, 0, 0), stroke_width=max(2, scale), stroke_fill=color)
    img.resize((size, size), Image.LANCZOS).save(dest)


def base_size(resource):
    m = re.search(r"(\d+)$", resource)
    return int(m.group(1)) if m else 16


def emit(directory, resource, ext, icon=None, glyph=None):
    """Emite SVG (preferido pelo ImageUtilities), PNG no tamanho base e
    PNG @2x para HiDPI, com os nomes originais dos resources."""
    outdir = os.path.join(OUT, directory)
    os.makedirs(outdir, exist_ok=True)
    size = base_size(resource)
    if icon:
        with open(os.path.join(LUCIDE, icon + ".svg")) as f:
            svg = f.read().replace("currentColor", COLOR)
        with open(os.path.join(outdir, f"{resource}.svg"), "w") as f:
            f.write(svg)
        render_lucide(icon, size, os.path.join(outdir, f"{resource}.{ext}"))
        render_lucide(icon, size * 2, os.path.join(outdir, f"{resource}@2x.{ext}"))
    else:
        render_php_glyph(glyph[0], glyph[1], size,
                         os.path.join(outdir, f"{resource}.{ext}"))
        render_php_glyph(glyph[0], glyph[1], size * 2,
                         os.path.join(outdir, f"{resource}@2x.{ext}"))


def main():
    count = 0
    for directory, items in TARGETS:
        for resource, ext, icon in items:
            emit(directory, resource, ext, icon=icon)
            count += 3
    for resource, (letter, color) in PHP_TYPES.items():
        emit(PHP_RES_DIR, resource, "png", glyph=(letter, color))
        count += 2
    print(f"gerados {count} arquivos em {OUT}")


if __name__ == "__main__":
    sys.exit(main())
