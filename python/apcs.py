from tkinter import Tk, Canvas, Frame, BOTH

class Window:
    root = None
    instance = None
    _frame = []

    _width = 500
    _height = 500

    _mousex = 0
    _mousey = 0
    _mouseclick = False
    _keypressed = {}

    @staticmethod
    def size(w, h):
        Window._width = w
        Window._height = h
        if Window.instance == None:
            Window.root = Tk()
            Window.instance = WindowInstance(Window.root)
            Window.root.geometry(str(Window._width) + "x" + str(Window._height))

    @staticmethod
    def width():
        return Window._width

    @staticmethod
    def height():
        return Window._height

    @staticmethod
    def start():
        Window.root.bind("<Motion>", Window._mouseMotion)
        Window.root.bind("<KeyPress>", Window._keyPressed)
        Window.root.bind("<KeyRelease>", Window._keyReleased)
        Window.root.bind("<ButtonPress-1>", Window._mouseClicked)
        Window.root.bind("<ButtonRelease-1>", Window._mouseReleased)
        Window.root.after(0, Window.drawFrame)
        Window.root.wm_attributes("-topmost" , -1)
        Window.root.after(1, lambda: Window.root.focus_force())
        Window.root.mainloop()

    @staticmethod
    def _mouseMotion(event):
        Window._mousex = event.x
        Window._mousey = event.y

    @staticmethod
    def _mouseClicked(event):
        Window._mouseclick = True

    @staticmethod
    def _mouseReleased(event):
        Window._mouseclick = False

    @staticmethod
    def _keyPressed(event):
        Window._keypressed[event.char] = True

    @staticmethod
    def _keyReleased(event):
        if event.char in Window._keypressed:
            del Window._keypressed[event.char]

    @staticmethod
    def drawFrame():
        Window.instance.clear()
        for f in Window._frame:
            f()
        Window.root.after(30, Window.drawFrame)

    @staticmethod
    def frame(cb):
        Window._frame.append(cb)

    class key:
        @staticmethod
        def pressed(k):
            return k in Window._keypressed

        @staticmethod
        def released(k):
            return k not in Window._keypressed

    class mouse:
        @staticmethod
        def getX():
            return Window._mousex

        @staticmethod
        def getY():
            return Window._mousey

        @staticmethod
        def clicked():
            return Window._mouseclick == True

    class out:

        @staticmethod
        def background(c):
            Window.instance.setColor(c)
            Window.instance.drawRectangle(0, 0, Window._width, Window._height)

        @staticmethod
        def color(c):
            Window.instance.setColor(c)

        @staticmethod
        def rectangle(x, y, w, h):
            Window.instance.drawRectangle(x - w / 2, y - h / 2, w, h)

        @staticmethod
        def square(x, y, s):
            Window.instance.drawRectangle(x - s / 2, y - s / 2, s, s)

        @staticmethod
        def oval(x, y, w, h):
            Window.instance.drawOval(x - w / 2, y - h / 2, w, h)

        @staticmethod
        def circle(x, y, r):
            Window.instance.drawOval(x - r, y - r, r * 2, r * 2)

        @staticmethod
        def line(x, y, ex, ey):
            Window.instance.drawLine(x, y, ex, ey)

        @staticmethod
        def font(fnt):
            Window.instance.font = fnt

        @staticmethod
        def text(txt, x, y):
            Window.instance.drawText(txt, x, y)


class WindowInstance(Frame):

    def __init__(self, parent):
        Frame.__init__(self, parent)
        self.parent = parent
        self.parent.title("Window")
        self.pack(fill=BOTH, expand=1)

        self.canvas = Canvas(self)
        self.doubleBuffer = False

        self.fill = "#000"
        self.outline = "#000"
        self.font = "Courier 16"

    def setColor(self, c):
        self.fill = c
        self.outline = c
    
    def setFill(self, f):
        self.fill = f
    
    def setOutline(self, o):
        self.outline = o

    def drawRectangle(self, x, y, w, h):
        self.canvas.create_rectangle(x, y, x + w, y + h, fill=self.fill, outline=self.outline)
        if not self.doubleBuffer:
            self.canvas.pack(fill=BOTH, expand=1)

    def drawLine(self, x, y, ex, ey):
        self.canvas.create_line(x, y, ex, ey, fill=self.fill)
        if not self.doubleBuffer:
            self.canvas.pack(fill=BOTH, expand=1)

    def drawOval(self, x, y, w, h):
        self.canvas.create_oval(x, y, x + w, y + h, fill=self.fill, outline=self.outline)
        if not self.doubleBuffer:
            self.canvas.pack(fill=BOTH, expand=1)

    def drawText(self, txt, x, y):
        self.canvas.create_text(x, y, text=txt, font=self.font, fill=self.fill)
        if not self.doubleBuffer:
            self.canvas.pack(fill=BOTH, expand=1)

    def frame(self):
        self.doubleBuffer = True
        self.canvas.pack(fill=BOTH, expand=1)

    def clear(self):
        self.canvas.delete("all")
