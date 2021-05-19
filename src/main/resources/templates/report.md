## Test status

| Success | Failed | Errored | Skipped |
|:-------:|:------:|:-------:|:-------:|
| {context.success} | {context.failed} | {context.errored} | {context.skipped} |
{#if config.extendedReport}
{#if context.errored > 0}
## Errored tests

{#for test : context.erroredNames}
<p>{test}</p>
{/for}
{/if}
{#if context.failed > 0}

## Failed tests

{#for test : context.failedNames}
<p>{test}</p>
{/for}
{/if}
{#if context.skipped > 0}

## Skipped tests

{#for test : context.skippedNames}
<p>{test}</p>
{/for}
{/if}
{/if}