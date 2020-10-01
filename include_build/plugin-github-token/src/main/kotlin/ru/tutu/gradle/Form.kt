package ru.tutu.gradle

import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingConstants.LEADING


class KotlinSwingStandardColoursEx(title: String) : JFrame() {

    init {
        createUI(title)
    }

    private fun createUI(message: String) {

        createLayout(
            JLabel(message, null, LEADING).apply {
                minimumSize = Dimension(90, 40)
                isOpaque = true
            }

        )

        setTitle("auth")
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
    }

    private fun createLayout(label: JLabel) {

        val gl = GroupLayout(contentPane)
        contentPane.layout = gl

        gl.autoCreateContainerGaps = true
        gl.autoCreateGaps = true


        gl.setHorizontalGroup(
            gl.createParallelGroup()
                .addGroup(
                    gl.createSequentialGroup()
                        .addComponent(label)
                )
        )

        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(label)
                )
        )

        pack()
    }
}

private fun createAndShowGUI() {

    val frame = KotlinSwingStandardColoursEx("Standard colours")
    frame.isVisible = true
}

fun main() {
    EventQueue.invokeLater(::createAndShowGUI)
}
