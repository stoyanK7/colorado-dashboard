import unittest
from unittest.mock import patch
from unittest.mock import MagicMock, Mock

import numpy
from tabulate import tabulate

from tasks.cleanup.cleanup import CleanupTasks
import pandas as pd
from config import aggregate_table_name_config, \
    clean_table_name_config, \
    last_seen_table_config, \
    read_table_name_config, \
    last_seen_column_name_config, preprocess_table_name_config


class TestCleanUpTasks(unittest.TestCase):

    @patch("tasks.cleanup.cleanup.CleanupTasks._cleanup_snapshot")
    @patch("tasks.cleanup.cleanup.CleanupTasks._cleanup_tables")
    def test_cleanup(self,
                                                 mock_cleanup_tables: MagicMock,
                                                 mock_cleanup_snapshot: MagicMock):
        ti = Mock()
        # Act
        CleanupTasks.cleanup(ti)

        # Assert
        mock_cleanup_tables.assert_any_call(aggregate_table_name_config)
        mock_cleanup_tables.assert_any_call(clean_table_name_config)
        mock_cleanup_tables.assert_any_call(read_table_name_config)
        mock_cleanup_tables.assert_any_call(preprocess_table_name_config)

        mock_cleanup_snapshot.assert_called_once()

    @patch("DAL.postgres_database_manager.PostgresDatabaseManager.delete_table")
    def test_cleanup_tables(self,
                                                 mock_cleanup_snapshot: MagicMock):
        ti = Mock()
        # Act
        CleanupTasks._cleanup_tables(aggregate_table_name_config)

        # Assert
        mock_cleanup_snapshot.assert_any_call('aggregatemediacategoryusage')
        mock_cleanup_snapshot.assert_any_call('aggregatemediatypespermachine')
        mock_cleanup_snapshot.assert_any_call('aggregatetoptenprintvolume')
        mock_cleanup_snapshot.assert_any_call('aggregateinkusage')
        mock_cleanup_snapshot.assert_any_call('aggregatesqmperprintmode')
