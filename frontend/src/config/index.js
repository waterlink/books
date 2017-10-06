const defaultApiGateway = 'http://localhost:9090';

export const config = {
  apiGateway: process.env.API_URL || defaultApiGateway,
};
