import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PayNotErrorMessages from './pay-not-error-messages';
import PayNotErrorMessagesDetail from './pay-not-error-messages-detail';
import PayNotErrorMessagesUpdate from './pay-not-error-messages-update';
import PayNotErrorMessagesDeleteDialog from './pay-not-error-messages-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayNotErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayNotErrorMessagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayNotErrorMessagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={PayNotErrorMessages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayNotErrorMessagesDeleteDialog} />
  </>
);

export default Routes;
