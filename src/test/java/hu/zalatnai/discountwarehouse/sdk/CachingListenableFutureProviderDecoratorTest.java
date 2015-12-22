package hu.zalatnai.discountwarehouse.sdk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.scheduling.annotation.AsyncResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachingListenableFutureProviderDecoratorTest {

    @Mock
    private ListenableFutureProvider<String, String> wrappedProvider;

    @InjectMocks
    private CachingListenableFutureProviderDecorator<String, String> sut;

    @Before
    public void setUp() throws Exception {
        when(wrappedProvider.get("key")).thenReturn(new AsyncResult<String>("value"));
    }

    @Test
    public void delegatesTheFirstGetCallForAGivenKeyToTheWrappedProvider() {
        sut.get("key");
        verify(wrappedProvider, times(1)).get("key");
    }

    @Test
    public void gettingAnEntryWithTheSameKeyMoreThanOnceOnlyResultsInOneCallToTheWrappedProvider() {
        sut.get("key");
        sut.get("key");
        verify(wrappedProvider, times(1)).get("key");
    }

    @Test
    public void theValueIdenfitifedByTheKeyIsSameRegardlessWhetherTheValueWasCached() throws Exception {
        assertEquals(sut.get("key").get(), "value");
        assertEquals(sut.get("key").get(), "value");
    }

    @Test(expected = RuntimeException.class)
    public void anyExceptionsThrownByTheWrappedProviderAreDelegatedCorrectly() throws Exception {
        RuntimeException exception = new RuntimeException("oh no");
        when(wrappedProvider.get("ohno")).thenThrow(exception);

        sut.get("ohno");
    }
}