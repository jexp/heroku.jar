package com.heroku.api.request.releases;

import com.heroku.api.Heroku;
import com.heroku.api.Release;
import com.heroku.api.exception.RequestFailedException;
import com.heroku.api.http.Http;
import com.heroku.api.request.Request;
import com.heroku.api.request.RequestConfig;

import java.util.HashMap;
import java.util.Map;

import static com.heroku.api.http.HttpUtil.noBody;
import static com.heroku.api.parser.Json.parse;

/**
 * TODO: Javadoc
 *
 * @author Naaman Newbold
 */
public class ReleaseInfo implements Request<Release> {

    private final RequestConfig config;

    public ReleaseInfo(String appName, String releaseName) {
        this.config = new RequestConfig().app(appName).with(Heroku.RequestKey.Release, releaseName);
    }
    
    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.GET;
    }

    @Override
    public String getEndpoint() {
        return Heroku.Resource.Release.format(config.get(Heroku.RequestKey.AppName), config.get(Heroku.RequestKey.Release));
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public String getBody() {
        throw noBody();
    }

    @Override
    public Http.Accept getResponseType() {
        return Http.Accept.JSON;
    }

    @Override
    public Map<String, String> getHeaders() {
        return new HashMap<String, String>();
    }

    @Override
    public Release getResponse(byte[] bytes, int status) {
        if (status == Http.Status.OK.statusCode) {
            return parse(bytes, getClass());
        }
        throw new RequestFailedException("Unable to retrieve release information.", status, bytes);
    }
}
