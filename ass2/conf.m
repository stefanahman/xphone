function [ X ] = conf( list )
% Confidence interval

alpha = 0.05;

t_alpha_2_120 = 1.98;

meanv = mean(list);
lengthv = length(list);
a = [];

for x = list
   a = (x-meanv).^2;
end

asum = sum(a);

s = sqrt(asum/(lengthv-1));

plusminus = (t_alpha_2_120*(s/sqrt(lengthv)));

X = [meanv,plusminus];

end

