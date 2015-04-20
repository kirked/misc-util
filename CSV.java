    package test;

    import java.util.stream.Stream;

    public class CSV {
      public static String[] split(final String text) {
        return (text.indexOf('"') < 0)? text.split(",")
                                      : splitCSV(Stream.<String>builder(), text, 0).build().toArray(String[]::new);
      }


      private static Stream.Builder<String> splitCSV(final Stream.Builder<String> accum, final String text, int start) {
        final int length = text.length();
        if (start >= length) return accum;

        final StringBuilder buf = new StringBuilder(length);
        boolean inquote = false;

        if (text.charAt(start) == '"') {
          inquote = true;
          start++;
        }

        int i = start;

      loop:
        for (; i < length; i++) {
          final char c = text.charAt(i);
          switch (c) {
            case '"':
              if (inquote) {
                if (i + 1 < length && text.charAt(i + 1) == '"') {
                  i++;
                  break;
                }
                else {
                  inquote = false;
                  continue loop;
                }
              }
              break;

            case '\\':
              if (i + 1 < length) {
                final char next = text.charAt(i + 1);
                switch (next) {
                  case 'b': buf.append('\b'); i++; continue loop;
                  case 'f': buf.append('\f'); i++; continue loop;
                  case 'r': buf.append('\r'); i++; continue loop;
                  case 'n': buf.append('\n'); i++; continue loop;
                  case 't': buf.append('\t'); i++; continue loop;
                  case '"': buf.append('"');  i++; continue loop;
                }
              }
              break;

            case ',':
              if (!inquote) {
                  // value termination
                i++;
                break loop;
              }
              break;
          }

          buf.append(c);
        }

        return splitCSV(accum.add(buf.toString()), text, i);
      }
    }