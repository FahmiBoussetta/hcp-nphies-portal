import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CovEliErrorMessages from './cov-eli-error-messages';
import CovEliErrorMessagesDetail from './cov-eli-error-messages-detail';
import CovEliErrorMessagesUpdate from './cov-eli-error-messages-update';
import CovEliErrorMessagesDeleteDialog from './cov-eli-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CovEliErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CovEliErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CovEliErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={CovEliErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CovEliErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
