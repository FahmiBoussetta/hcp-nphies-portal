import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './response-insurance-item.reducer';
import { IResponseInsuranceItem } from 'app/shared/model/response-insurance-item.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsuranceItem = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const responseInsuranceItemList = useAppSelector(state => state.responseInsuranceItem.entities);
  const loading = useAppSelector(state => state.responseInsuranceItem.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="response-insurance-item-heading" data-cy="ResponseInsuranceItemHeading">
        <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.title">Response Insurance Items</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.createLabel">Create new Response Insurance Item</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {responseInsuranceItemList && responseInsuranceItemList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.category">Category</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.excluded">Excluded</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.network">Network</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.unit">Unit</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.term">Term</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.responseInsurance">Response Insurance</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {responseInsuranceItemList.map((responseInsuranceItem, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${responseInsuranceItem.id}`} color="link" size="sm">
                      {responseInsuranceItem.id}
                    </Button>
                  </td>
                  <td>{responseInsuranceItem.category}</td>
                  <td>{responseInsuranceItem.excluded ? 'true' : 'false'}</td>
                  <td>{responseInsuranceItem.name}</td>
                  <td>{responseInsuranceItem.description}</td>
                  <td>{responseInsuranceItem.network}</td>
                  <td>{responseInsuranceItem.unit}</td>
                  <td>{responseInsuranceItem.term}</td>
                  <td>
                    {responseInsuranceItem.responseInsurance ? (
                      <Link to={`response-insurance/${responseInsuranceItem.responseInsurance.id}`}>
                        {responseInsuranceItem.responseInsurance.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${responseInsuranceItem.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${responseInsuranceItem.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${responseInsuranceItem.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.home.notFound">No Response Insurance Items found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ResponseInsuranceItem;
