package examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.pholser.junit.quickcheck.From;
import examples.AsciiStringGenerator;
import examples.JavaScriptCodeGenerator;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import static org.junit.Assume.*;

@RunWith(JQF.class)
public class CompilerTestNew {
    
    private Context context;

    @Before
    public void initContext() {
        context = Context.enter();
    }

    @After
    public void exitContext() {
        context.exit();
    }

    @Fuzz
    public void testWithString(@From(AsciiStringGenerator.class) String input) {
        try {
            // Scriptable scope = context.initStandardObjects();
            // Object result = context.evaluateString(scope, input, "input", 0, null);
            Script script = context.compileString(input, "input", 0, null);
        } catch (EvaluatorException e) {
            Assume.assumeNoException(e);
        }

    }

    @Fuzz
    public void testWithGenerator(@From(JavaScriptCodeGenerator.class) String code) {
        testWithString(code);
    }

}
