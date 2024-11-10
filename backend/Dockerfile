FROM python:3.9-slim
WORKDIR /backend
COPY ./src/requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY ./src ./src 
ENV PYTHONUNBUFFERED=1
ENV FLASK_APP=src/app.py
#ENV FLASK_APP=src/init-test.py
EXPOSE 5000


CMD ["python", "./src/app.py"]
#CMD ["python", "./src/init-test.py"]
