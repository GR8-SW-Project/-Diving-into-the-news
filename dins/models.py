from django.db import models

# Create your models here.
class News(models.Model):
    field_id = models.AutoField(db_column='_id', primary_key=True)  # Field renamed because it started with '_'.
    title = models.CharField(max_length=1024)
    headline = models.CharField(max_length=1024)
    date_news = models.DateTimeField()
    news_link = models.CharField(max_length=1024)
    content = models.TextField()
    category = models.CharField(max_length=32)
    site = models.CharField(max_length=32)

    class Meta:
        managed = False
        db_table = 'news'