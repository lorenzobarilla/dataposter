import archives.localArchive
import org.openrndr.Fullscreen
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.draw.loadFont
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.gui.GUI
import org.openrndr.shape.Rectangle
import org.openrndr.writer

fun main() = application{
    configure {

        width = 700
        height = ((width*7)/5)


    }

    program {
        val archive = localArchive("archives/music").iterator()
        var article = archive.next()
        val gui = GUI()
        val scaleFactor = 1.0

        val composite = compose {
            layer {
                drawer.clear(ColorRGBa.PINK)
            }

            layer{
                val font = loadFont("data/fonts/PPNeueMontreal-Medium.ttf", 64.0 * scaleFactor)
                val largeFont = loadFont("data/fonts/PPNeueMontreal-Medium.ttf", 144.0 * scaleFactor)
                val smallFont = loadFont("data/fonts/PPNeueMontreal-Book.ttf", 24.0 * scaleFactor)

                draw {
                    drawer.fontMap = largeFont
                    drawer.isolated {
                        drawer.translate(0.0 + 40.0, (height * 1.0)-40.0)

                        drawer.text("2000", 0.0, 0.0,)
                    }
                }
            }
        }

    extend {
            composite.draw(drawer)
        }

    }
}