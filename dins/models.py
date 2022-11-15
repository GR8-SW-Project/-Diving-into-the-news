from django.db import models

# Create your models here.
class News(models.Model):
    field_id = models.AutoField(db_column='_id', primary_key=True)  # Field renamed because it started with '_'.
    title = models.CharField(max_length=1024)
    headline = models.CharField(max_length=1024)
    date = models.DateField()
    link = models.CharField(max_length=1024)
    content = models.TextField()
    category = models.CharField(max_length=32)
    site = models.CharField(max_length=32)

    class Meta:
        managed = False
        db_table = 'news'

class Keywords(models.Model):
    date = models.DateField(primary_key=True)
    category = models.CharField(max_length=32)
    keyword = models.CharField(max_length=256)
    importance = models.DecimalField(max_digits=5, decimal_places=4)

    class Meta:
        managed = False
        db_table = 'keywords'
        unique_together = (('date', 'keyword', 'category'),)

class KeywordsWeek(models.Model):
    date_start = models.DateField(primary_key=True)
    date_end = models.DateField()
    category = models.CharField(max_length=32)
    keyword = models.CharField(max_length=256)
    importance = models.DecimalField(max_digits=5, decimal_places=4)

    class Meta:
        managed = False
        db_table = 'keywords_week'
        unique_together = (('date_start', 'date_end', 'category', 'keyword'),)

class KeywordsMonth(models.Model):
    month = models.IntegerField(primary_key=True)
    category = models.CharField(max_length=32)
    keyword = models.CharField(max_length=256)
    importance = models.DecimalField(max_digits=5, decimal_places=4)

    class Meta:
        managed = False
        db_table = 'keywords_month'
        unique_together = (('month', 'category', 'keyword'),)