package work

import archives.LoadedArticle
import archives.localArchive
import com.google.gson.Gson
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.rgb
import org.openrndr.draw.loadFont
import org.openrndr.events.Event
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.post
import org.openrndr.extra.fx.shadow.DropShadow
import org.openrndr.extra.gui.GUI
import org.openrndr.extra.gui.addTo
import org.openrndr.extra.parameters.ActionParameter
import org.openrndr.extra.parameters.Description
import org.openrndr.extras.imageFit.imageFit
import org.openrndr.shape.Rectangle
import org.openrndr.writer

fun main() = application {
    configure {
        width = 600
        height = 800
    }
    program {
        val archive = localArchive("archives/music").iterator()
        var article = archive.next()
        val gui = GUI()

        val onNewArticle = Event<LoadedArticle>()
        var musicData: Array<MusicData> = emptyArray()

        onNewArticle.listen {
            musicData = Gson().fromJson(article.texts[0], Array<MusicData>::class.java)
        }

        val settings = @Description("Settings") object {
            @ActionParameter("Next article")
            fun nextArticle() {
                article = archive.next()
                onNewArticle.trigger(article)
            }
        }

        val composite = compose {
            var background = ColorRGBa.PINK
            onNewArticle.listen {
                background = rgb(Math.random(), Math.random(), Math.random())
            }

            /*layer {
                val font = loadFont("data/fonts/IBMPlexMono-Bold.ttf",32.0)
                draw {
                    drawer.fontMap = font

                    for (md in musicData) {
                        drawer.text(md.artist, 100.0, 100.0)
                    }


                }
            }*/

            layer {
                //DURATION
                var maxDuration = 240.0
                var maxHeightSurface = height*0.9
                var rowHeight: Double = 0.0

                draw {
                    var rowY = 0.0

                    drawer.fill = ColorRGBa.WHITE

                    for (md in musicData) {
                        //calcoliamo l'altezza rowHeight in proporzione
                        rowHeight = ((150-md.duration)/(150-maxDuration))*(maxHeightSurface/musicData.size)

                        drawer.rectangle(0.0, rowY, width * 1.0, rowHeight)
                        rowY += rowHeight + 10.0
                    }

                }
            }

        }
        onNewArticle.trigger(article)

        gui.add(settings)
        extend(gui)
        extend {
            gui.visible = mouse.position.x < 200.0
            composite.draw(drawer)
        }
    }
}