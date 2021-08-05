package org.to2mbn.jmccc.mcdownloader.download.combine;

import org.to2mbn.jmccc.mcdownloader.download.ResultProcessor;
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter;

import java.util.Objects;

class ExtendedDownloadTaskCombinedDownloadTask<R, S> extends CombinedDownloadTask<S> {

    private final CombinedDownloadTask<R> prev;
    private final ResultProcessor<R, CombinedDownloadTask<S>> next;

    public ExtendedDownloadTaskCombinedDownloadTask(CombinedDownloadTask<R> prev, ResultProcessor<R, CombinedDownloadTask<S>> next) {
        Objects.requireNonNull(prev);
        Objects.requireNonNull(next);

        this.prev = prev;
        this.next = next;
    }

    @Override
    public void execute(final CombinedDownloadContext<S> context) throws Exception {
        context.submit(prev, new CallbackAdapter<R>() {

            @Override
            public void done(R result1) {
                try {
                    context.submit(next.process(result1), new CallbackAdapter<S>() {

                        @Override
                        public void done(S result2) {
                            context.done(result2);
                        }

                    }, true);
                } catch (Throwable e) {
                    context.failed(e);
                }
            }

        }, true);
    }

}
