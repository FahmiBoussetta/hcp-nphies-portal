import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Quantity from './quantity';
import QuantityDetail from './quantity-detail';
import QuantityUpdate from './quantity-update';
import QuantityDeleteDialog from './quantity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QuantityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QuantityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QuantityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Quantity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QuantityDeleteDialog} />
  </>
);

export default Routes;
