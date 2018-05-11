package com.starkindustries.fruitsamurai.Engine;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
/**
 * This class manages the GLFW window.
 * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
 * @author Aszalós Roland
 * @version 1.0
 * @since Fruit Samurai 0.1
 */
public class Window {
    private long windowID;

    private int width;
    private int height;
    private boolean vsync;
    private boolean resized = false;
    private String windowtitle;
    private GLFWCursorPosCallback posCallback;
    private final WindowOptions opts;
    private double mouseX;
    private double mouseY;
    private boolean fullscreen;
    public  boolean[] keys = new boolean[65536];
    public  char [] lastChar;
    private int elapsedTime;
    private boolean inputEnabled;

    /**
     * Standard constructor initializes the window object.
     * @param title The title of the window
     * @param width The width of the window
     * @param height The height of the window
     * @param vSync Toggles VSync
     * @param opts A {@link Window.WindowOptions} object to set additional window options
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public Window(String title, int width, int height, boolean vSync,WindowOptions opts) {
        this.windowtitle = title;
        this.width = width;
        this.height = height;
        this.vsync = vSync;
        this.resized = false;
        this.fullscreen = false;
        this.opts = opts;
    }
    /**
     * Initializes the window object.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        if (opts.compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        // Create the window
        windowID = glfwCreateWindow(width, height, windowtitle, NULL, NULL);
        if (windowID == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
        glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            elapsedTime = 0;
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
            if (isKeyPressed(GLFW_KEY_F12) && fullscreen){
                glfwSetWindowMonitor(windowID,0L,GLFW_DONT_CARE,GLFW_DONT_CARE,width,height,GLFW_DONT_CARE);
                fullscreen = !fullscreen;
            }
            else if(isKeyPressed(GLFW_KEY_F12))
            {
                glfwSetWindowMonitor(windowID, glfwGetPrimaryMonitor(), GLFW_DONT_CARE, GLFW_DONT_CARE, width, height, GLFW_DONT_CARE);
                fullscreen = !fullscreen;
            }
        });
        glfwSetCursorPosCallback(windowID, posCallback = GLFWCursorPosCallback.create((window, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        }));

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                windowID,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);

        if (isvSync()) {
            // Enable v-sync
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(windowID);

        GL.createCapabilities();
        //GLUtil.setupDebugMessageCallback();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        if (opts.showTriangles) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        if (opts.antialiasing) {
            glfwWindowHint(GLFW_SAMPLES, 4);
        }
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    /**
     * Sets the clear color to the desired color.
     *@param r red component of the color
     *@param g green component of the color
     *@param b blue component of the color
     *@param alpha alpha component of the color
     */
    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    /**
     * @param keyCode a GLFW keycode
     * @return a boolean whether a specific key has been pressed or not
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowID, keyCode) == GLFW_PRESS;
    }
    /**
     * @param keyCode a GLFW keycode
     * @return a boolean whether a specific key has been released or not
     */
    public boolean isKeyReleased(int keyCode) {
        return glfwGetKey(windowID, keyCode) == GLFW_RELEASE;
    }

    /**
     * @param keyCode a GLFW keycode
     * @return a boolean whether a specific mouse key is pressed or not
     */
    public boolean isMouseKeyPressed(int keyCode) {
        return glfwGetMouseButton(windowID, keyCode) == GLFW_PRESS;
    }
    /**
     * @param keyCode a GLFW keycode
     * @return a boolean whether a specific mouse key is released or not
     */
    public boolean isMouseKeyReleased(int keyCode) {
        return glfwGetMouseButton(windowID, keyCode) == GLFW_RELEASE;
    }

    /**
     * @return a boolean whether to close the window or not
     */
    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowID);
    }
    /**
     * This method closes the window.
     */
    public void closeWindow()
    {
        glfwSetWindowShouldClose(windowID, true);
    }
    /**
     * @return the width of the window
     */
    public int getWidth() {
        return width;
    }
    /**
     * @return the height of the window
     */
    public int getHeight() {
        return height;
    }
    /**
     * @return a boolean whether the window has been resized or not
     */
    public boolean isResized() {
        return resized;
    }
    /**
     * @param resized sets resized to desired boolean
     */
    public void setResized(boolean resized) {
        this.resized = resized;
    }
    /**
     * @return a boolean whether the window has vSync or not
     */
    public boolean isvSync() {
        return vsync;
    }
    /**
     * @param vsync sets vSync
     */
    public void setvsync(boolean vsync) {
        this.vsync = vsync;
    }

    /**
     * Updates the window.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void update() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();
    }
    /**
     * @return a double containing the x position of the mouse
     */
    public double getMouseX() {
        return mouseX;
    }
    /**
     * @return a double containing the y position of the mouse
     */
    public double getMouseY() {
        return mouseY;
    }
    /**
     * Hides the mouse.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void hideMouse() {
        glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }
    /**
     * Shows the mouse.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void showMouse() {
        glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
    /**
     * Changes the cursor.
     * @param desiredCursor is a {@link org.lwjgl.glfw.GLFW#GLFW_CURSOR_NORMAL} or any other cursor integer constant.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void setCursor(int desiredCursor){
        glfwSetCursor(windowID, glfwCreateStandardCursor(desiredCursor));
    }
    /**
     * Enables text input from the user.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void enableInput(){
        inputEnabled = true;
        glfwSetCharCallback(windowID, (window,codepoint)->{
            lastChar = Character.toChars(codepoint);
        });
    }
    /**
     * Disables text input from the user.
     * See <a href="http://www.glfw.org/documentation.html">GLFW</a> for more information about the GLFW methods used.
     */
    public void disableInput(){
        inputEnabled = false;
        glfwSetCharCallback(windowID, null);
    }
    /**
     * @return the last char entered by the user while the input was enabled.
     */
    public char[] getLastChar() {
        return lastChar;
    }
    /**
     * @return the window options.
     */
    public WindowOptions getOptions() {
        return opts;
    }
    /**
     * @param title sets the window title to the desired string.
     */
    public void setWindowTitle(String title) {
        glfwSetWindowTitle(windowID, title);
    }
    /**
     * @return the window title.
     */
    public String getWindowTitle() {
        return windowtitle;
    }
    /**
     * @return the window ID.
     */
    public long getWindowID(){
        return windowID;
    }
    /**
     * Restores the window state.
     * Used by the {@link com.starkindustries.fruitsamurai.Graphics.Hud} class.
     */
    public void restoreState() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    /**
     * This class manages additional window options.
     * @author Aszalós Roland
     * @version 1.0
     * @since Fruit Samurai 0.1
     */
    public static class WindowOptions {
        public boolean showTriangles;
        public boolean showFps;
        public boolean compatibleProfile;
        public boolean antialiasing;
    }

    /**
     * @param elapsedTime sets the elapsed time to the given value.
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    /**
     * @return the elapsed time.
     */
    public int getElapsedTime() {
        return elapsedTime;
    }
    /**
     * @return whether the input is enabled or not.
     */
    public boolean isInputEnabled() {
        return inputEnabled;
    }
}
