package ru.tutu.gradle

import java.awt.Dimension
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.SwingConstants.LEADING


interface Form {
    fun close()
}

fun openFrameWithCopyText(title: String, label: String, text: String): Form {
    System.setProperty("java.awt.headless", "false")
    var frame: JFrame? = null
    SwingUtilities.invokeLater {
        frame = FrameWithCopyMessage(title, label, text)
        frame?.isVisible = true
    }
    return object : Form {
        override fun close() {
            frame?.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
        }
    }
}

class FrameWithCopyMessage(title: String, label: String, text: String) : JFrame() {

    init {
        setTitle(title)
        val jLabel = JLabel(label, null, LEADING).apply {
            minimumSize = Dimension(90, 40)
            isOpaque = true
        }
        val jText = JTextField(text)

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl
        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true
        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(jLabel)
                )
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(jText)
                )
        )
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(jLabel)
                )
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(jText)
                )
        )
        pack()
//        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

}

fun main() {
    openFrameWithCopyText("title", "label", "text")
}
