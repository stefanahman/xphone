# Input data modeling

- Use the data collections given to you to find the real underlying distributions of call initiation, call durations, car speeds, initiation position.

* For each of the data collections:
- Make histograms of the data to determine the distribution to use
May be necessary to test with different interval widths

- Estimate the parameters for the chosen distribution(lambda; my; sigma, etc.)

- Does the distribution fit? Use the Chi-Square test to perform goodness-of-fit test of the provided data and the chosen distribution.

We made histogram of the data we've got. By plotting in Mathlab we could estimate each distribution.

[Picture]

To estimate the required parameters for each distribution, we used the following estimators: 

* Arrival time: Exponential
lambda = 1/mean(X)
Calculate the mean value of X (list of data). Divide 1 by the mean of X to get lambda.

* Duration: Exponential
lambda = 1/mean(X)
Same as 'Arrival time'.

* Position: Uniform
b = (n+1)n*max(X)
To get 'b', multiply the top value in your list (X) with (n+1)/n.

* Speed: Normal
my = mean(X), sigma^2 = var(X)
Calculate the mean and variance to get the two parameters. 


