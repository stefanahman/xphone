function [ sumchi,df ] = chi2test( list,interval,param,lambda )
%Chi2Test
%Input:     [list,interval,parameters,lambda]
%Output:    [X0,Degrees of freedom]

interval_a = 1/interval;
lengthoflist = length(list);
expected = lengthoflist/interval;

a = [];
for x = 0:interval_a:1
   a = [a -1*(log(1-x)/lambda)]; %#ok<AGROW>
end

obs = zeros(1,interval);
j = 1;
while(j < lengthoflist+1)
    for i = 1:interval
        if (a(i) <= list(j) && list(j) < a(i+1))
            obs(i) = obs(i) + 1;
        end
    end
    j = j + 1;
end

chilist = [];
for l = 1:interval
    chilist = [chilist ((obs(l)-expected)^2/expected)]; %#ok<AGROW>
end

sumchi = sum(chilist);

df = interval - param - 1;

end

