package presentation.core.platform.source.image

/**
 * Renders info widgets (calendar, quote) onto a grayscale pixel array.
 *
 * All drawing is pure pixel manipulation with no platform dependencies.
 * Intended to be called after dithering for crisp black/white output.
 */
public object WidgetRenderer {

    /**
     * Available widget overlays for sleep screen images.
     */
    public enum class WidgetType { NONE, CALENDAR, QUOTE }

    /**
     * Draws a widget onto the grayscale array in-place.
     *
     * @param gray Grayscale pixel data.
     * @param width Image width.
     * @param height Image height.
     * @param widget Widget type to render.
     * @param year Current year.
     * @param month Current month (1–12).
     * @param quote Quote text (used only when [widget] is [WidgetType.QUOTE]).
     */
    public fun render(
        gray: IntArray,
        width: Int,
        height: Int,
        widget: WidgetType,
        year: Int,
        month: Int,
        quote: String = "",
    ) {
        when (widget) {
            WidgetType.NONE -> {}
            WidgetType.CALENDAR -> drawCalendar(gray, width, height, year, month)
            WidgetType.QUOTE -> drawQuote(gray, width, height, quote)
        }
    }

    // ── Calendar widget ─────────────────────────────────────────────────

    private fun drawCalendar(gray: IntArray, width: Int, height: Int, year: Int, month: Int) {
        val cellW = 20
        val cellH = 16
        val cols = 7
        val headerH = 18
        val calW = cols * cellW
        val days = daysInMonth(year, month)
        val startDay = dayOfWeek(year, month, 1)
        val rows = (days + startDay + 6) / 7
        val calH = headerH + rows * cellH

        val marginX = 12
        val marginY = 12
        val boxW = calW + marginX * 2
        val boxH = calH + marginY * 2
        val x0 = width - boxW - 8
        val y0 = height - boxH - 8

        fillRect(gray, width, height, x0, y0, boxW, boxH, 255)
        drawRect(gray, width, height, x0, y0, boxW, boxH, 0)

        val monthNames = arrayOf(
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC",
        )
        val title = "${monthNames[(month - 1).coerceIn(0, 11)]} $year"
        val titleX = x0 + marginX + (calW - title.length * 6) / 2
        drawString(gray, width, height, titleX, y0 + marginY, title, 0)

        val dayLabels = "Su Mo Tu We Th Fr Sa"
        drawString(gray, width, height, x0 + marginX, y0 + marginY + 10, dayLabels, 0)

        val gridY0 = y0 + marginY + headerH
        var day = 1
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val idx = row * cols + col
                if (idx >= startDay && day <= days) {
                    val label = if (day < 10) " $day" else "$day"
                    val cx = x0 + marginX + col * cellW
                    val cy = gridY0 + row * cellH
                    drawString(gray, width, height, cx + 4, cy + 4, label, 0)
                    day++
                }
            }
        }
    }

    // ── Quote widget ────────────────────────────────────────────────────

    private fun drawQuote(gray: IntArray, width: Int, height: Int, quote: String) {
        if (quote.isBlank()) return
        val maxCharsPerLine = (width - 48) / 6
        val lines = wrapText(quote, maxCharsPerLine)

        val lineH = 10
        val marginX = 16
        val marginY = 10
        val boxW = width - 16
        val boxH = lines.size * lineH + marginY * 2
        val x0 = 8
        val y0 = height - boxH - 8

        fillRect(gray, width, height, x0, y0, boxW, boxH, 0)
        drawRect(gray, width, height, x0, y0, boxW, boxH, 255)

        for ((i, line) in lines.withIndex()) {
            drawString(gray, width, height, x0 + marginX, y0 + marginY + i * lineH, line, 255)
        }
    }

    // ── Text rendering ──────────────────────────────────────────────────

    private fun drawString(gray: IntArray, width: Int, height: Int, x: Int, y: Int, text: String, color: Int) {
        var cx = x
        for (ch in text) {
            drawChar(gray, width, height, cx, y, ch, color)
            cx += 6
        }
    }

    private fun drawChar(gray: IntArray, width: Int, height: Int, x: Int, y: Int, ch: Char, color: Int) {
        val index = ch.code - 32
        if (index < 0 || index >= FONT.size / 5) return
        val offset = index * 5
        for (col in 0 until 5) {
            val bits = FONT[offset + col].toInt() and 0xFF
            for (row in 0 until 7) {
                if ((bits shr row) and 1 == 1) {
                    setPixel(gray, width, height, x + col, y + row, color)
                }
            }
        }
    }

    // ── Primitive drawing ───────────────────────────────────────────────

    private fun fillRect(gray: IntArray, width: Int, height: Int, x: Int, y: Int, w: Int, h: Int, color: Int) {
        for (dy in 0 until h) {
            for (dx in 0 until w) {
                setPixel(gray, width, height, x + dx, y + dy, color)
            }
        }
    }

    private fun drawRect(gray: IntArray, width: Int, height: Int, x: Int, y: Int, w: Int, h: Int, color: Int) {
        for (dx in 0 until w) {
            setPixel(gray, width, height, x + dx, y, color)
            setPixel(gray, width, height, x + dx, y + h - 1, color)
        }
        for (dy in 0 until h) {
            setPixel(gray, width, height, x, y + dy, color)
            setPixel(gray, width, height, x + w - 1, y + dy, color)
        }
    }

    private fun fillCircle(gray: IntArray, width: Int, height: Int, cx: Int, cy: Int, r: Int, color: Int) {
        for (dy in -r..r) {
            for (dx in -r..r) {
                if (dx * dx + dy * dy <= r * r) {
                    setPixel(gray, width, height, cx + dx, cy + dy, color)
                }
            }
        }
    }

    private fun setPixel(gray: IntArray, width: Int, height: Int, x: Int, y: Int, color: Int) {
        if (x in 0 until width && y in 0 until height) {
            gray[y * width + x] = color
        }
    }

    // ── Date helpers ────────────────────────────────────────────────────

    private fun daysInMonth(year: Int, month: Int): Int = when (month) {
        1 -> 31; 2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        3 -> 31; 4 -> 30; 5 -> 31; 6 -> 30
        7 -> 31; 8 -> 31; 9 -> 30; 10 -> 31; 11 -> 30; 12 -> 31
        else -> 30
    }

    /**
     * Zeller-like day-of-week: 0 = Sunday, 6 = Saturday.
     */
    private fun dayOfWeek(year: Int, month: Int, day: Int): Int {
        var y = year
        var m = month
        if (m < 3) {
            m += 12
            y -= 1
        }
        val k = y % 100
        val j = y / 100
        val h = (day + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7
        return ((h + 6) % 7)
    }

    // ── Text wrapping ───────────────────────────────────────────────────

    private fun wrapText(text: String, maxWidth: Int): List<String> {
        if (maxWidth <= 0) return listOf(text)
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var current = StringBuilder()
        for (word in words) {
            if (current.isEmpty()) {
                current.append(word)
            } else if (current.length + 1 + word.length <= maxWidth) {
                current.append(" ").append(word)
            } else {
                lines.add(current.toString())
                current = StringBuilder(word)
            }
        }
        if (current.isNotEmpty()) lines.add(current.toString())
        return lines
    }

    // ── 5×7 bitmap font (ASCII 32–126) ─────────────────────────────────
    // Each character is 5 columns × 7 rows, stored column-major.
    // Bit 0 = top row, bit 6 = bottom row.

    @Suppress("MaxLineLength")
    private val FONT = byteArrayOf(
        // ' ' (32)
        0x00, 0x00, 0x00, 0x00, 0x00,
        // '!' (33)
        0x00, 0x00, 0x5F, 0x00, 0x00,
        // '"' (34)
        0x00, 0x07, 0x00, 0x07, 0x00,
        // '#' (35)
        0x14, 0x7F, 0x14, 0x7F, 0x14,
        // '$' (36)
        0x24, 0x2A, 0x7F, 0x2A, 0x12,
        // '%' (37)
        0x23, 0x13, 0x08, 0x64, 0x62,
        // '&' (38)
        0x36, 0x49, 0x55, 0x22, 0x50,
        // ''' (39)
        0x00, 0x05, 0x03, 0x00, 0x00,
        // '(' (40)
        0x00, 0x1C, 0x22, 0x41, 0x00,
        // ')' (41)
        0x00, 0x41, 0x22, 0x1C, 0x00,
        // '*' (42)
        0x14, 0x08, 0x3E, 0x08, 0x14,
        // '+' (43)
        0x08, 0x08, 0x3E, 0x08, 0x08,
        // ',' (44)
        0x00, 0x50, 0x30, 0x00, 0x00,
        // '-' (45)
        0x08, 0x08, 0x08, 0x08, 0x08,
        // '.' (46)
        0x00, 0x60, 0x60, 0x00, 0x00,
        // '/' (47)
        0x20, 0x10, 0x08, 0x04, 0x02,
        // '0' (48)
        0x3E, 0x51, 0x49, 0x45, 0x3E,
        // '1' (49)
        0x00, 0x42, 0x7F, 0x40, 0x00,
        // '2' (50)
        0x42, 0x61, 0x51, 0x49, 0x46,
        // '3' (51)
        0x21, 0x41, 0x45, 0x4B, 0x31,
        // '4' (52)
        0x18, 0x14, 0x12, 0x7F, 0x10,
        // '5' (53)
        0x27, 0x45, 0x45, 0x45, 0x39,
        // '6' (54)
        0x3C, 0x4A, 0x49, 0x49, 0x30,
        // '7' (55)
        0x01, 0x71, 0x09, 0x05, 0x03,
        // '8' (56)
        0x36, 0x49, 0x49, 0x49, 0x36,
        // '9' (57)
        0x06, 0x49, 0x49, 0x29, 0x1E,
        // ':' (58)
        0x00, 0x36, 0x36, 0x00, 0x00,
        // ';' (59)
        0x00, 0x56, 0x36, 0x00, 0x00,
        // '<' (60)
        0x08, 0x14, 0x22, 0x41, 0x00,
        // '=' (61)
        0x14, 0x14, 0x14, 0x14, 0x14,
        // '>' (62)
        0x00, 0x41, 0x22, 0x14, 0x08,
        // '?' (63)
        0x02, 0x01, 0x51, 0x09, 0x06,
        // '@' (64)
        0x32, 0x49, 0x79, 0x41, 0x3E,
        // 'A' (65)
        0x7E, 0x11, 0x11, 0x11, 0x7E,
        // 'B' (66)
        0x7F, 0x49, 0x49, 0x49, 0x36,
        // 'C' (67)
        0x3E, 0x41, 0x41, 0x41, 0x22,
        // 'D' (68)
        0x7F, 0x41, 0x41, 0x22, 0x1C,
        // 'E' (69)
        0x7F, 0x49, 0x49, 0x49, 0x41,
        // 'F' (70)
        0x7F, 0x09, 0x09, 0x09, 0x01,
        // 'G' (71)
        0x3E, 0x41, 0x49, 0x49, 0x7A,
        // 'H' (72)
        0x7F, 0x08, 0x08, 0x08, 0x7F,
        // 'I' (73)
        0x00, 0x41, 0x7F, 0x41, 0x00,
        // 'J' (74)
        0x20, 0x40, 0x41, 0x3F, 0x01,
        // 'K' (75)
        0x7F, 0x08, 0x14, 0x22, 0x41,
        // 'L' (76)
        0x7F, 0x40, 0x40, 0x40, 0x40,
        // 'M' (77)
        0x7F, 0x02, 0x0C, 0x02, 0x7F,
        // 'N' (78)
        0x7F, 0x04, 0x08, 0x10, 0x7F,
        // 'O' (79)
        0x3E, 0x41, 0x41, 0x41, 0x3E,
        // 'P' (80)
        0x7F, 0x09, 0x09, 0x09, 0x06,
        // 'Q' (81)
        0x3E, 0x41, 0x51, 0x21, 0x5E,
        // 'R' (82)
        0x7F, 0x09, 0x19, 0x29, 0x46,
        // 'S' (83)
        0x46, 0x49, 0x49, 0x49, 0x31,
        // 'T' (84)
        0x01, 0x01, 0x7F, 0x01, 0x01,
        // 'U' (85)
        0x3F, 0x40, 0x40, 0x40, 0x3F,
        // 'V' (86)
        0x1F, 0x20, 0x40, 0x20, 0x1F,
        // 'W' (87)
        0x3F, 0x40, 0x38, 0x40, 0x3F,
        // 'X' (88)
        0x63, 0x14, 0x08, 0x14, 0x63,
        // 'Y' (89)
        0x07, 0x08, 0x70, 0x08, 0x07,
        // 'Z' (90)
        0x61, 0x51, 0x49, 0x45, 0x43,
        // '[' (91)
        0x00, 0x7F, 0x41, 0x41, 0x00,
        // '\' (92)
        0x02, 0x04, 0x08, 0x10, 0x20,
        // ']' (93)
        0x00, 0x41, 0x41, 0x7F, 0x00,
        // '^' (94)
        0x04, 0x02, 0x01, 0x02, 0x04,
        // '_' (95)
        0x40, 0x40, 0x40, 0x40, 0x40,
        // '`' (96)
        0x00, 0x01, 0x02, 0x04, 0x00,
        // 'a' (97)
        0x20, 0x54, 0x54, 0x54, 0x78,
        // 'b' (98)
        0x7F, 0x48, 0x44, 0x44, 0x38,
        // 'c' (99)
        0x38, 0x44, 0x44, 0x44, 0x20,
        // 'd' (100)
        0x38, 0x44, 0x44, 0x48, 0x7F,
        // 'e' (101)
        0x38, 0x54, 0x54, 0x54, 0x18,
        // 'f' (102)
        0x08, 0x7E, 0x09, 0x01, 0x02,
        // 'g' (103)
        0x0C, 0x52, 0x52, 0x52, 0x3E,
        // 'h' (104)
        0x7F, 0x08, 0x04, 0x04, 0x78,
        // 'i' (105)
        0x00, 0x44, 0x7D, 0x40, 0x00,
        // 'j' (106)
        0x20, 0x40, 0x44, 0x3D, 0x00,
        // 'k' (107)
        0x7F, 0x10, 0x28, 0x44, 0x00,
        // 'l' (108)
        0x00, 0x41, 0x7F, 0x40, 0x00,
        // 'm' (109)
        0x7C, 0x04, 0x18, 0x04, 0x78,
        // 'n' (110)
        0x7C, 0x08, 0x04, 0x04, 0x78,
        // 'o' (111)
        0x38, 0x44, 0x44, 0x44, 0x38,
        // 'p' (112)
        0x7C, 0x14, 0x14, 0x14, 0x08,
        // 'q' (113)
        0x08, 0x14, 0x14, 0x18, 0x7C,
        // 'r' (114)
        0x7C, 0x08, 0x04, 0x04, 0x08,
        // 's' (115)
        0x48, 0x54, 0x54, 0x54, 0x20,
        // 't' (116)
        0x04, 0x3F, 0x44, 0x40, 0x20,
        // 'u' (117)
        0x3C, 0x40, 0x40, 0x20, 0x7C,
        // 'v' (118)
        0x1C, 0x20, 0x40, 0x20, 0x1C,
        // 'w' (119)
        0x3C, 0x40, 0x30, 0x40, 0x3C,
        // 'x' (120)
        0x44, 0x28, 0x10, 0x28, 0x44,
        // 'y' (121)
        0x0C, 0x50, 0x50, 0x50, 0x3C,
        // 'z' (122)
        0x44, 0x64, 0x54, 0x4C, 0x44,
        // '{' (123)
        0x00, 0x08, 0x36, 0x41, 0x00,
        // '|' (124)
        0x00, 0x00, 0x7F, 0x00, 0x00,
        // '}' (125)
        0x00, 0x41, 0x36, 0x08, 0x00,
        // '~' (126)
        0x10, 0x08, 0x08, 0x10, 0x08,
    )
}
