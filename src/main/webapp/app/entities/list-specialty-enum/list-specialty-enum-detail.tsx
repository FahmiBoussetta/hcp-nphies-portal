import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './list-specialty-enum.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IListSpecialtyEnumDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListSpecialtyEnumDetail = (props: IListSpecialtyEnumDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { listSpecialtyEnumEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="listSpecialtyEnumDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.listSpecialtyEnum.detail.title">ListSpecialtyEnum</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{listSpecialtyEnumEntity.id}</dd>
          <dt>
            <span id="s">
              <Translate contentKey="hcpNphiesPortalApp.listSpecialtyEnum.s">S</Translate>
            </span>
          </dt>
          <dd>{listSpecialtyEnumEntity.s}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.listSpecialtyEnum.practitionerRole">Practitioner Role</Translate>
          </dt>
          <dd>{listSpecialtyEnumEntity.practitionerRole ? listSpecialtyEnumEntity.practitionerRole.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/list-specialty-enum" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/list-specialty-enum/${listSpecialtyEnumEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ listSpecialtyEnum }: IRootState) => ({
  listSpecialtyEnumEntity: listSpecialtyEnum.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListSpecialtyEnumDetail);
