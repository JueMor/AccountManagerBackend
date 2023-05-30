FROM nginx:alpine

WORKDIR /nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3128
ENTRYPOINT [ "nginx" ]
CMD [ "-g", "daemon off;" ]