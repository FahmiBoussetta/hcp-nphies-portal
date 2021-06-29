import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './list-communication-medium-enum.reducer';
import { IListCommunicationMediumEnum } from 'app/shared/model/list-communication-medium-enum.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListCommunicationMediumEnum = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const listCommunicationMediumEnumList = useAppSelector(state => state.listCommunicationMediumEnum.entities);
  const loading = useAppSelector(state => state.listCommunicationMediumEnum.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="list-communication-medium-enum-heading" data-cy="ListCommunicationMediumEnumHeading">
        <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.home.title">List Communication Medium Enums</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.home.createLabel">
              Create new List Communication Medium Enum
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {listCommunicationMediumEnumList && listCommunicationMediumEnumList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.cm">Cm</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.communication">Communication</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {listCommunicationMediumEnumList.map((listCommunicationMediumEnum, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${listCommunicationMediumEnum.id}`} color="link" size="sm">
                      {listCommunicationMediumEnum.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`hcpNphiesPortalApp.CommunicationMediumEnum.${listCommunicationMediumEnum.cm}`} />
                  </td>
                  <td>
                    {listCommunicationMediumEnum.communication ? (
                      <Link to={`communication/${listCommunicationMediumEnum.communication.id}`}>
                        {listCommunicationMediumEnum.communication.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`${match.url}/${listCommunicationMediumEnum.id}`}
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
                        to={`${match.url}/${listCommunicationMediumEnum.id}/edit`}
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
                        to={`${match.url}/${listCommunicationMediumEnum.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.listCommunicationMediumEnum.home.notFound">
                No List Communication Medium Enums found
              </Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ListCommunicationMediumEnum;
