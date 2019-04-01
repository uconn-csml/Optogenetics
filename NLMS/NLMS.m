% Normalized least mean square algorithm
% The algorithm is used to estimate parameter values of the pRS316-tagRFP-psd protein turnover system 
% and for the following estimated system equation: Y_hat(n) = p_hat_y(n-1)*y(n-1) + p_hat_pro(n-1) + p_hat_deg(n-1)*u_deg(n-1)
% where n is the discrete-time index, Y_hat(n) is estimated normalized intensity of pRS316-tagRFP-psd+ fluorescence at time n,
% y(n) is the normalized intensity of pRS316-tagRFP-psd fluorescence at time n,
% u_deg(n) is the normalized intensity of blue LED (degradation factor) at time n, and p_hat_y, p_hat_pro, and p_hat_deg are
% estimated parameters of endogenous degradation, production, and light-induced degradation, respectively.

% @author Bahareh Mahrou

iteration = 300;%-----------------------------------% number of iterations
Y_hat = zeros(iteration , 1);%----------------------% define a vector variable for the estimated pRS316-tagRFP-psd protein turnover system
e = zeros(iteration , 1);%--------------------------% define a vector variable for the error between the measured intensity of pRS316-tagRFP-psd+, y, and the estimated intensity of pRS316-tagRFP-psd+, Y_hat
Mu = zeros(iteration , 1);%-------------------------% define a vector variable for the self-adjustable chosen step size
P_hat = zeros(iteration , 3);%----------------------% define a vector variable for the estimated parameters

Mu0 = 0.1;%-----------------------------------------% choose a fixed value for step size
Y_hat(1) = 1;%--------------------------------------% choose an initial value for Y_hat
p_hat_y = 0.9;%-------------------------------------% choose an initial value for the estimated parameter of endogenous degradation 
p_hat_pro = 0.01;%----------------------------------% choose an initial value for the estimated parameter of production
p_hat_deg = -0.01;%---------------------------------% choose an initial value for the estimated parameter of light-induced degradation

P_hat(1,:) = [p_hat_y; p_hat_pro; p_hat_deg];
for n = 1 : (iteration - 1)
    U = [y(n) 1 u_deg(n)];%-------------------------% set input vector of the normalized measured intensity of pRS316-tagRFP-psd+, production input (approximating to 1 assuming galactose supply is always sufficient) , and the normalized intensity of degradation input (blue LED light) respectively
    e(n) = y(n) - Y_hat(n);%------------------------% measure error
    Y_hat(n+1) = P_hat(n,:)*U';%--------------------% estimate the amount of pRS316-tagRFP-psd+    
    Mu(n) = Mu0/(U*U');%----------------------------% update step size
    P_hat(n+1,:) = P_hat (n,:) + Mu(n)*e(n)*U;%-----% update parameters for the next step
end

%% plot the parameters in time
time = 1:iteration;
figure
plot(time,P_hat(:,1));
hold
plot(time,P_hat(:,2));
plot(time,P_hat(:,3))