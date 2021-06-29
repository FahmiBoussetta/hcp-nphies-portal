import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './list-role-code-enum.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ListRoleCodeEnumDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const listRoleCodeEnumEntity = useAppSelector(state => state.listRoleCodeEnum.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listRoleCodeEnumDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.detail.title">ListRoleCodeEnum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listRoleCodeEnumEntity.id}</dd>
          <dt>
            <span id="r">
              <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.r">R</Translate>
            </span>
          </dt>
          <dd>{listRoleCodeEnumEntity.r}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.listRoleCodeEnum.practitionerRole">Practitioner Role</Translate>
          </dt>
          <dd>{listRoleCodeEnumEntity.practitionerRole ? listRoleCodeEnumEntity.practitionerRole.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/list-role-code-enum" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/list-role-code-enum/${listRoleCodeEnumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ListRoleCodeEnumDetail;
