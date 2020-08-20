package jdbi.benchmark;

import org.jdbi.v3.core.extension.ExtensionMethod;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.core.statement.TemplateEngine;

import java.util.Objects;

public class DropwizardNamePrependingTemplateEngine implements TemplateEngine {
	private final TemplateEngine originalEngine;

	/**
	 * @param originalEngine    The original {@link TemplateEngine} to use to create the actual statements
	 */
	public DropwizardNamePrependingTemplateEngine(TemplateEngine originalEngine) {
		this.originalEngine = Objects.requireNonNull(originalEngine, "'originalEngine' cannot be null'");
	}

	@Override
	public String render(String template, StatementContext ctx) {
		final ExtensionMethod extensionMethod = ctx.getExtensionMethod();
		final String originalResult = originalEngine.render(template, ctx);

		if (extensionMethod == null) {
			return originalResult;
		}

		final StringBuilder query = new StringBuilder(originalResult.length() + 100);
		query.append("/* ");
		final String className = extensionMethod.getType().getSimpleName();
		if (!className.isEmpty()) {
			query.append(className).append('.');
		}
		query.append(extensionMethod.getMethod().getName());
		query.append(" */ ");
		query.append(originalResult);

		return query.toString();
	}
}

